import http from 'k6/http';
import {check} from "k6";
import moment from "cdnjs.com/libraries/moment.js/2.18.1";
import {htmlReport} from 'https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js';

export let options = {
    throw: true,
    scenarios: {
        contacts: {
            executor: 'constant-arrival-rate',
            duration: '30s',
            rate: 500,
            timeUnit: '1s',
            preAllocatedVUs: 400,
            maxVUs: 400,
        },
    },

    thresholds: {
        "http_req_duration": [`p(95) < ${__ENV.REQ_DURATION_THRESHOLDS}`],
    },
};

export default function () {
    let response = http.get("http://host.docker.internal:8081");
    let length = response.body.length
    check(length, {"check body length(happycase) ": (v) => v >= 0});
};

export function handleSummary(data) {
    return {
        [`./results/${moment().format('YYYYMMDDHHms')}_summary.html`]: htmlReport(data, {filename: `dummy`}),
    };
}
