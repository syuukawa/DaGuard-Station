const keys = {
    toke: "Auth-Token",
    timestamp: "Token-Timestamp",
    get: "Token-Get",
    update: "Token-Update",
    delete: "Token-Delete",
    null: "Token-Null"
};


const retrive = () => {
    return sessionStorage.getItem(keys.toke);
};
const store = (token, timestamp = null) => {
    const addTime = timestamp ? timestamp : Date.now();
    sessionStorage.setItem(keys.toke, token);
    sessionStorage.setItem(keys.timestamp, addTime);
    const data = JSON.stringify({
        [keys.toke]: sessionStorage.getItem(keys.toke),
        [keys.timestamp]: sessionStorage.getItem(keys.timestamp)
    });
    broadcast(keys.update, data);
};
const remove = () => {
    sessionStorage.clear();
};


const broadcast = (key, data = null) => {
    localStorage.setItem(key, data);
    localStorage.removeItem(key);
};

const sessionStorage_transfer = (event) => {
    if (!event) {
        event = window.event;
    }
    if (event.newValue) {
        switch (event.key) {
            case keys.get: {
                if (retrive()) {
                    const data = JSON.stringify({
                        [keys.toke]: sessionStorage.getItem(keys.toke),
                        [keys.timestamp]: sessionStorage.getItem(keys.timestamp)
                    });
                    broadcast(keys.update, data);
                }
                break;
            }
            case keys.update: {
                const data = JSON.parse(event.newValue.toString());
                const localAddTime = sessionStorage.getItem(keys.timestamp);
                if (!localAddTime || localAddTime < data[keys.timestamp]) {
                    store(data[keys.toke], data[keys.timestamp]);
                }
                break;
            }
            case keys.null: {
                break;
            }
            default:
                break;
        }
    }
};

function init() {
    window.addEventListener("storage", sessionStorage_transfer, false);
    broadcast(keys.get);
}

export default {
    init,
    retrive,
    store,
    remove,
};