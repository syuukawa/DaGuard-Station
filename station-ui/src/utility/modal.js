const UIkit = window.UIkit;
const $ = window.jQuery;

export function showModalWithDelay(modalId, tryTimes = 1) {
    const modal = $("#" + modalId);
    if (UIkit.modal(modal)) {
        UIkit.modal(modal).show();
    } else {
        if (tryTimes <= 10) {
            tryTimes = tryTimes + 1;
            setTimeout(() => {
                showModalWithDelay(modalId, tryTimes)
            }, 1000);
        } else {
            console.error("can't find modal " + modalId)
        }
    }
}


export function hideModal(modalId) {
    const modal = $("#" + modalId);
    if (UIkit.modal(modal)) {
        UIkit.modal(modal).hide();
    }
}