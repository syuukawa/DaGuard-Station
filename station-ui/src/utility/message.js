export function getTemplateString(template = {}, code, data = {}) {
    if (!template[code]) {
        return code || "";
    }
    let test = template[code].toString().slice(0);
    let regex = /\${data\..*?}/g;
    let found = test.match(regex);
    if (!found) {
        return code;
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