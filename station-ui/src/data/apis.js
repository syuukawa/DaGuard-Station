export const apis = {
    user: {
        register: "/user/register",
        activate: "/user/activate/${data.email}/${data.token}",
        login: "/user/login",
        logout: "/user/logout",
        forgotPassword: "/user/forgot-password",
        verifyForgotPassword: "/user/verify-forgot-password/${email}/${token}",
        resetPassword: "/user/reset-password",
        changePassword: "/user/change-password",
        self: "/user/self",
        updateProfile: "/user/update-profile",
        twoFactorSecret: "user/two-factor/secret",
        activateTwoFactor: "/user/two-factor/activate",
        twoFactorSettings: "/user/two-factor/settings",
        updateTwoFactor: "/user/two-factor/update",
        apikeys: "/user/apikeys",
        deleteApikey: "/user/apikeys/${apikey}"
    }
};