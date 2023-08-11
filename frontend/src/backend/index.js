import {init} from './appFetch';
import * as userService from './userService';
import * as billboardService from './billboardService'
import * as purchaseService from './purchaseService'
import * as ticketDeliveryService from './ticketDeliveryService'
export {default as NetworkError} from "./NetworkError";

// eslint-disable-next-line
export default {init, userService,billboardService, purchaseService, ticketDeliveryService};
