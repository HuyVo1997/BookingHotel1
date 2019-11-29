package com.bookinghotel.service;

import com.bookinghotel.filter.Encryptor;
import com.bookinghotel.model.Booking;
import com.bookinghotel.model.Room;
import com.bookinghotel.repository.bookingRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PaypalService {
    @Autowired
    APIContext apiContext;

    @Autowired
    bookingRepository bookingRepository;

    @Autowired
    roomService roomService;

    @Autowired
    bookingService bookingService;

    public Payment createPayment(Double total, String currency,String method,String intent,String description,
                                 String cancleUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f",total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment =  new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancleUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        return payment.create(apiContext);
    }

    public Payment excutePayment(String paymentId,String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext,paymentExecute);
    }

    public void refund(String code) throws PayPalRESTException {
        Refund refund = new Refund();
        Amount amount = new Amount();
        Booking booking = bookingRepository.findBookingByCodetransaction(code);
        amount.setTotal(booking.getPrice().toString());
        amount.setCurrency("USD");
        refund.setAmount(amount);
        Room room = roomService.findRoomById(booking.getRoom().getRoomid());
        room.setNumroom(room.getNumroom() + booking.getQuantity());
        booking.setCodetransaction(null);
        booking.setCurrent(0);
        booking.setSecretkey(null);
        String decodedString = Encryptor.decrypt(code,booking.getSecretkey());
        System.out.println(decodedString);
        Sale sale = new Sale();
        sale.setId(decodedString);
        try {
            // Refund sale
            sale.refund(apiContext, refund);
            roomService.updateRoom(room);
            bookingService.saveBooking(booking);
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }
    }
}
