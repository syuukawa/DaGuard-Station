export function merge(var1 = {}, var2 = {}) {
    return Object.assign({}, var1, var2);
}

export function mergeChild(var1 = {}, key, var2 = {}) {
    if (!var1[key]) {
        var1[key] = {};
    }
    return merge(var1, {
        [key]: merge(var1[key], var2)
    });
}


export function isValidEmail(email) {
    let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}


export function excuteFunc(func, data) {
    if (typeof func === 'function') {
        func(data);
    }
}

export function isBlank(value) {
    return !(value && value.toString().trim() !== "");
}

