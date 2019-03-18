export function assembleUrlQueryString(obj) {
    const paramArr = [];
    let param = '';
    if (obj && typeof obj === 'object') {
        if (paramArr.length === 0) {
            paramArr.push('?');
        }
        for (const i in obj) {
            if (obj.hasOwnProperty(i)) {
                if (obj[i] !== '' && obj[i] !== null && typeof obj[i] !== 'undefined') {
                    paramArr.push(i);
                    paramArr.push('=');
                    paramArr.push(obj[i]);
                    paramArr.push('&');
                }
            }
        }
        param = paramArr.join('').replace(/&$/, '');
        return param;
    }
}

export function getUrlSearchParams(queryString) {

    let params = {};
    if (typeof queryString !== 'string') {
        return params;
    }

    queryString = queryString.trim().replace(/^[?#&]/, '');

    if (!queryString) {
        return params;
    }
    for (const param of queryString.split('&')) {
        let [key, value] = param.replace(/\+/g, ' ').split('=');
        params[key] = value;
    }
    return params;
}

export function getPathVariableUrl(template, data = {}) {
    if (!template) {
        return "";
    }
    let test = template.toString().slice(0);
    let regex = /\${data\..*?}/g;
    let found = test.match(regex);
    if (!found) {
        return template;
    }
    let keys = found.map(f => {
        return f.toString().replace("${data.", "").replace("}", "")
    });
    keys.forEach(key => {
        const templateKey = "${data." + key + "}";
        test = test.split(templateKey).join(data && data[key] ? data[key] : "");
    });
    return test;
}