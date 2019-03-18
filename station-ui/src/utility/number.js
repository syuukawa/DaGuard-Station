import numeral from 'numeral';

export function formatNumber(num, deciaml = -1) {
    let formatString = "";
    if (num) {
        if (deciaml === -1) {
            let arr = num.toString().split(".");
            if (arr[1]) {
                let length = arr[1].length;
                for (let i = 0; i < length; i++) {
                    formatString += "0"
                }
            }
        } else {
            for (let i = 0; i < deciaml; i++) {
                formatString += "0"
            }
        }
        if (num) {
            if (formatString === "") {
                return numeral(num).format("0,0");
            } else {
                return numeral(num).format("0,0." + formatString);
            }
        }
    }
    return 0;
}


export function get24HourChangeRate(change, price) {
    const prefix = change > 0 ? "+" : "";
    const open = (Number(change) + Number(price));
    const rate = open > 0 ? numeral((Number(change) / open) * 100).format('0,0.00') : 0;
    return prefix + rate;
}


export function getFormattedPrice(instrument, price) {
    if (!isNaN(price)) {
        if (instrument && instrument.tickSize) {
            const tickSize = instrument.tickSize;
            if (!isNaN(tickSize) && Number(tickSize) < 1 && Number(tickSize) > 0) {
                const format = '0,' + tickSize.toString().replace("1", "0");
                return numeral(Number(price)).format(format).toString().replace(/\.?0+$/, '');
            }
        }
        return numeral(Number(price)).format('0,0.00').toString().replace(/\.?0+$/, '');
    }
    return 0;
}