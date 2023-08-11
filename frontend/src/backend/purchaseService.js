import {appFetch, config} from "./appFetch";

export const buy = (sessionId, transactionNumber, numberOfTickets, onSuccess, onErrors) =>
    appFetch(`/purchase/${sessionId}/buy`,
        config('POST', {transactionNumber, numberOfTickets}), onSuccess, onErrors);

export const history=(page,onSuccess)=>
    appFetch(`/purchase/history?page=${page}`,
        config('GET'),onSuccess)