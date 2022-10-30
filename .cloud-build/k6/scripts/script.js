import http from 'k6/http';
import { check } from "k6";

export let options = {
    ext: {
        loadimpact: {
            projectID: 3606293,
            name: "streaming to cloud"
        }},
    duration: '10s',
    vus: 10,

    thresholds: {
        "http_req_duration": [`p(95) < ${__ENV.REQ_DURATION_THRESHOLDS}`],
    },
};

export default function () {
    let response = http.get("http://tiny-gateway-vm.us-central1-c.c.phrasal-bivouac-366409.internal");
    let length = response.body.length
    check(length, { "body length ": (v) => v >= 0});
};
