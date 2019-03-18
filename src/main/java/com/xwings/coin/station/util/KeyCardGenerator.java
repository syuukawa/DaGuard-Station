package com.xwings.coin.station.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public final class KeyCardGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(KeyCardGenerator.class);
    private static final ThreadLocal<SimpleDateFormat> LOCAL_DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static final int LINE_LENGTH = 550;

    public static byte[] generateKeyCard(String coin, String userKey, String backupKey, String walletKey) throws IOException, WriterException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        final PDDocument document = new PDDocument();
        final PDPage pdPage = new PDPage(PDRectangle.A4);
        document.addPage(pdPage);
        final InputStream secondPage = Thread.currentThread().getContextClassLoader().getResourceAsStream("keycard/second-GAI.pdf");
        final PDDocument secondDocument = PDDocument.load(secondPage);
        final PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
        try {
            contentStream.beginText();
            contentStream.newLineAtOffset(370, 763);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 30);
            contentStream.setNonStrokingColor(getColor("#000000"));
            contentStream.showText("KeyCard");
            contentStream.endText();

            final PDImageXObject pdImage = getPdImageXObject(document, coin);
            contentStream.drawImage(pdImage, 28, 748, 310, 41);

            contentStream.beginText();
            contentStream.newLineAtOffset(37, 725);
            contentStream.setFont(PDType1Font.HELVETICA, 14);
            contentStream.setNonStrokingColor(getColor("#9B9B9B"));
            final String message = String.format("Created %s Wallet on %s",
                    coin, LOCAL_DATE_FORMAT.get().format(new Timestamp(System.currentTimeMillis())));
            contentStream.showText(message);
            contentStream.endText();

            contentStream.addRect(35, 672, 520, 38);
            contentStream.setNonStrokingColor(getColor("#FFE5E5"));
            contentStream.fill();

            contentStream.beginText();
            contentStream.newLineAtOffset(100, 685);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.setNonStrokingColor(getColor("#E21E1E"));
            contentStream.showText("Print this document, or keep it securely offline. See Second page for FAQ.");
            contentStream.endText();

            drawData(document, contentStream, "A: User Key", "This is your private key, encrypted with your passcode",
                    userKey.replaceAll(" ", ""), 490);
            drawData(document, contentStream, "B: Backup Key", "This is your private key, encrypted with your passcode",
                    backupKey.replaceAll(" ", ""), 310);
            drawData(document, contentStream, "C: Encrypted wallet Password", "This is the wallet password, encrypted with a key held by Daguard",
                    walletKey.replaceAll(" ", ""), 130);

            final PDPage secondDocumentPage = secondDocument.getPage(0);
            document.addPage(secondDocumentPage);
        } finally {
            contentStream.close();
            document.save(byteArrayOutputStream);
            secondDocument.close();
            document.close();
        }

        return byteArrayOutputStream.toByteArray();
    }

    private static PDImageXObject getPdImageXObject(PDDocument document, String coin) throws IOException {
        final String path = "keycard/" + coin.toLowerCase() + ".png";
        LOG.info("read image: {}", path);

        final InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

        return PDImageXObject.createFromByteArray(document, IOUtils.toByteArray(resourceAsStream), path);
    }

    private static void drawData(PDDocument document, PDPageContentStream contentStream, String header, String description, String data, float ty) throws IOException, WriterException {
        drawBitMatrix(document, contentStream, data, ty);

        contentStream.beginText();
        contentStream.newLineAtOffset(207, ty + 142);
        contentStream.setFont(PDType1Font.HELVETICA, 16);
        contentStream.setNonStrokingColor(getColor("#000000"));
        contentStream.showText(header);
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(207, ty + 126);
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.setNonStrokingColor(getColor("#4C4C4C"));
        contentStream.showText(description);
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(207, ty + 101);
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        contentStream.setNonStrokingColor(getColor("#4C4C4C"));
        contentStream.showText("Data:");
        contentStream.endText();

        final float tx = 207f;
        float currentTy = ty + 87;
        final char[] chars = data.toCharArray();
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chars.length; currentTy -= 14) {
            int lineSize = 0;
            while (i < chars.length && lineSize <= LINE_LENGTH) {
                final char aChar = chars[i];
                stringBuilder.append(aChar);
                lineSize += getCharLength(aChar);

                i++;
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(tx, currentTy);
            contentStream.setFont(PDType1Font.HELVETICA, 9);
            contentStream.setNonStrokingColor(getColor("#000000"));
            contentStream.showText(stringBuilder.toString());
            contentStream.endText();

            stringBuilder.delete(0, stringBuilder.length());
        }
    }

    private static void drawBitMatrix(PDDocument document, PDPageContentStream contentStream, String data, float ty) throws WriterException, IOException {
        final QRCodeWriter qrCodeWriter = new QRCodeWriter();
        final BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 1540, 1540,
                MapBuilder.<EncodeHintType, Object>standard().with(EncodeHintType.MARGIN, 0).with(EncodeHintType.CHARACTER_SET, "UTF-8").create());
        final ByteArrayOutputStream matrixStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", matrixStream);

        final PDImageXObject qrImage = PDImageXObject.createFromByteArray(document, matrixStream.toByteArray(), "matrix");
        contentStream.drawImage(qrImage, 35, ty, 154, 154);
    }

    private static int getCharLength(char a) {
        if (a == '\'') {
            return 3;
        }

        if (a == ':' || a == '\\' || a == '/') {
            return 4;
        }

        if (a == '{' || a == '}') {
            return 5;
        }

        if (a == '\"') {
            return 6;
        }

        if (Character.isLowerCase(a) || Character.isDigit(a)) {
            return 8;
        }

        if (a == '+' || a == '=') {
            return 9;
        }

        return 10;
    }

    /**
     * #9B9B9B
     *
     * @param hexString
     * @return
     */
    private static Color getColor(String hexString) {
        final int colorValue = Numbers.hexToBigInteger(hexString.replace("#", "")).intValue();

        return new Color((colorValue >> 16) & 0xFF, (colorValue >> 8) & 0xFF, colorValue & 0xFF);
    }

    private KeyCardGenerator() {
        throw new AssertionError("No instances for you!");
    }

}
