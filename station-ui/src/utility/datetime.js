export function getDateTime(time, trailingSecond = true) {
    function getzf(num) {
        if (parseInt(num, 10) < 10) {
            num = '0' + num;
        }
        return num;
    }

    let date = new Date(time);
    let dateTimeStr = date.getFullYear() + "-" + getzf(date.getMonth() + 1) + "-" + getzf(date.getDate()) + " " + getzf(date.getHours()) + ":" + getzf(date.getMinutes());
    if (trailingSecond) {
        dateTimeStr += ":" + getzf(date.getSeconds());
    }
    return dateTimeStr;
}

export function getTime12(time, trailingSecond = true) {
    function getzf(num) {
        if (parseInt(num, 10) < 10) {
            num = '0' + num;
        }
        return num;
    }

    let date = new Date(time);
    let hours = getzf(date.getHours());
    let suffix = "AM"
    if (hours > 12) {
        hours -= 12;
        suffix = "PM";
    }
    let dateTimeStr = hours + ":" + getzf(date.getMinutes());
    if (trailingSecond) {
        dateTimeStr += ":" + getzf(date.getSeconds());
    }
    return dateTimeStr + " " + suffix;
}

export function getTime(time, trailingSecond = true) {
    function getzf(num) {
        if (parseInt(num, 10) < 10) {
            num = '0' + num;
        }
        return num;
    }

    let date = new Date(time);
    let dateTimeStr = getzf(date.getHours()) + ":" + getzf(date.getMinutes());
    if (trailingSecond) {
        dateTimeStr += ":" + getzf(date.getSeconds());
    }
    return dateTimeStr;
}