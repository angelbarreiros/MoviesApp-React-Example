package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.exceptions.AlreadyStartedSessionException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.TimeExceededException;
import es.udc.paproject.backend.model.services.BillboardService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/cinema")

public class BillboardController {
    private final static String TIME_EXCEEDED_EXCEPTION_CODE = "project.exceptions.TimeExceededException";

    @Autowired
    private MessageSource messageSource;
    @ExceptionHandler(TimeExceededException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleMaxQuantityExceededException(TimeExceededException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(TIME_EXCEEDED_EXCEPTION_CODE,
                new Object[] {exception.getAllowedDays()}, TIME_EXCEEDED_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @Autowired
    private BillboardService billboardService;
    @GetMapping(path = "/billboard")
    public List<BillboardRowDto> getBillboard(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws TimeExceededException {
        return BillboardRowConversor.toBillboardDtos(billboardService.completeBillboard(date));
    }
    @GetMapping(path = "/session/{id}")
    public SessionDto getSession(@Validated @PathVariable("id") Long id) throws InstanceNotFoundException, AlreadyStartedSessionException {
        return SessionConversor.toSessionDto(billboardService.findSessionById(id));
    }

    @GetMapping(path = "/movie/{id}")
    public MovieDto getMovie(@Validated @PathVariable("id") Long id) throws InstanceNotFoundException {
        return MovieConversor.toMovieDto(billboardService.getMovie(id));
    }
}
