import {appFetch, config} from "./appFetch";

export const ticketDelivery = (sessionId, transactionNumber, purchaseId, onSuccess, onErrors) =>
    appFetch(`/purchase/delivery`,
        config('POST', {transactionNumber, purchaseId}), onSuccess, onErrors);