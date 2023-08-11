package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.PurchaseService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.*;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private MessageSource messageSource;

    private final static String INVALID_CART_EXCEPTION = "project.exceptions.InvalidCardException";
    private final static String ALREADY_USED_EXCEPTION = "project.exceptions.AlreadyUsedException";
    private final static String FULL_SESSION_EXCEPTION = "project.exceptions.FullSessionException";
    private final static String PURCHASE_NOT_FOUND_EXCEPTION = "project.exceptions.PurchaseNotFoundException";

    @ExceptionHandler(FullSessionException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleFullSessionException(FullSessionException exception, Locale locale){
        String  errorMessage = messageSource.getMessage(FULL_SESSION_EXCEPTION, new Object[]{exception.getFreeSeats()}, FULL_SESSION_EXCEPTION,
                locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(AlreadyUsedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleAlreadyUsedException(Locale locale){
        String  errorMessage = messageSource.getMessage(ALREADY_USED_EXCEPTION, null, ALREADY_USED_EXCEPTION,
                locale);
        return new ErrorsDto(errorMessage);
    }




    @ExceptionHandler(InvalidCardException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleInvalidCardException(InvalidCardException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(INVALID_CART_EXCEPTION, new Object[]{exception.getCardNumber()}, INVALID_CART_EXCEPTION, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(PurchaseNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handlePurchaseNotFoundException(PurchaseNotFoundException exception, Locale locale){
        String  errorMessage = messageSource.getMessage(PURCHASE_NOT_FOUND_EXCEPTION, new Object[]{exception.getMessage()}, PURCHASE_NOT_FOUND_EXCEPTION,
                locale);
        return new ErrorsDto(errorMessage);
    }

    @Autowired
    private PurchaseService purchaseService;
    @GetMapping(path = "/history")
    public BlockDto<PurchaseHistoryDto> getHistory(@RequestAttribute Long userId,
                                                   @RequestParam(defaultValue="0") int page){
        Block<Purchase> purchaseBlock=purchaseService.history(userId,page, Purchase.PAGE_SIZE);
        return new BlockDto<>(PurchaseHistoryConversor.toPurchaseDtos(purchaseBlock.getItems()),purchaseBlock.getExistMoreItems());
    }

    @PostMapping(path = "/{sessionId}/buy")
    public Long buy(@RequestAttribute Long userId, @PathVariable("sessionId") Long sessionId, @Validated @RequestBody PurchaseDto params)
            throws InstanceNotFoundException, AlreadyStartedSessionException, FullSessionException {
        return purchaseService.buy(params.getNumberOfTickets(),
                sessionId,
                userId,
                params.getTransactionNumber()).getId();
    }
    @PostMapping(path = "/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ticketDelivery(@RequestAttribute Long userId, @Validated @RequestBody TicketDeliveryDto params)
        throws PurchaseNotFoundException, SessionNotFoundException, AlreadyUsedException,
            AlreadyStartedSessionException, InvalidCardException{
        purchaseService.ticketDelivery(
                params.getPurchaseId(),
                params.getTransactionNumber());
    }
}
