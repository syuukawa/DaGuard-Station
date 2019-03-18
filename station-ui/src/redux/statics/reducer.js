import labels from "../../data/international/en/labels";
import templates from "../../data/international/en/templates";

const initialState = {
    labels: labels,
    templates: templates
};

export default (state = initialState, action = {}) => {
    switch (action.type) {
        default: {
            return state;
        }
    }
};