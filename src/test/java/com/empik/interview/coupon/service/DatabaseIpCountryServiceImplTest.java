package com.empik.interview.coupon.service;

import com.empik.interview.coupon.entity.IpCountry;
import com.empik.interview.coupon.exception.CannotResolveIpCountryException;
import com.empik.interview.coupon.repository.IpCountryRepository;
import com.empik.interview.coupon.service.impl.DatabaseIpCountryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Created by Dominik Zieliński
 * Date: 18/04/2026
 * Time: 08:46
 */
@ExtendWith(MockitoExtension.class)
public class DatabaseIpCountryServiceImplTest {

    @Mock
    private IpCountryRepository ipCountryRepository;

    @InjectMocks
    private DatabaseIpCountryServiceImpl service;

    @Test
    void resolveCountryCodeWhenPrefixMatchesShouldReturnCountryCode() {
        IpCountry longPrefix = new IpCountry();
        longPrefix.setIpPrefix("10.0.");
        longPrefix.setCountryCode("PL");

        IpCountry shortPrefix = new IpCountry();
        shortPrefix.setIpPrefix("10.");
        shortPrefix.setCountryCode("DE");

        when(ipCountryRepository.findAllByOrderByIpPrefixDesc()).thenReturn(List.of(longPrefix, shortPrefix));

        String result = service.resolveCountryCode("10.0.1.8");

        assertEquals("PL", result);
    }

    @Test
    void resolveCountryCodeWhenNoPrefixMatchesShouldThrowException() {
        IpCountry entry = new IpCountry();
        entry.setIpPrefix("192.168.");
        entry.setCountryCode("PL");

        when(ipCountryRepository.findAllByOrderByIpPrefixDesc()).thenReturn(List.of(entry));

        CannotResolveIpCountryException ex = assertThrows(CannotResolveIpCountryException.class,
                () -> service.resolveCountryCode("10.0.1.8"));

        assertEquals("Cannot resolve country for IP", ex.getMessage());
    }
}
