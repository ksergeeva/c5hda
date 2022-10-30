import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class MessageTest {

    @Mock
    GeoService geoServiceMock;
    @Mock
    LocalizationService localizationServiceMock;

    @Test
    @DisplayName("GSI-USA")
    void TestGeoServiceImpl() {
        GeoService geoService = new GeoServiceImpl();
        String NEW_YORK_IP = "96.44.183.149";
        Location locationExpected = new Location("New York", Country.USA, " 10th Avenue", 32);
        Location locationActual = geoService.byIp(NEW_YORK_IP);
        Assertions.assertEquals(locationExpected.getCity(), locationActual.getCity());
    }

    @Test
    @DisplayName("GSI-RUS")
    void TestGeoServiceImplR() {
        GeoService geoService = new GeoServiceImpl();
        String MOSCOW_IP = "172.0.32.11";
        Location locationExpected = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        Location locationActual = geoService.byIp(MOSCOW_IP);
        Assertions.assertEquals(locationExpected.getCity(), locationActual.getCity());
    }

    @Test
    @DisplayName("LSI")
    void TestLocalizationServiceImpl() {
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Assertions.assertEquals("Добро пожаловать", localizationServiceMock.locale(Country.RUSSIA));
    }

    @Test
    @DisplayName("RUSSIA")
    void TestRussia() {
        Mockito.when(geoServiceMock.byIp(Mockito.any())).thenReturn(new Location(null, Country.RUSSIA, null, 0));
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        MessageSender messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        Assertions.assertEquals("Добро пожаловать", messageSender.send(Mockito.anyMap()));
    }

    @Test
    @DisplayName("USA")
    void TestUSA() {
        Mockito.when(geoServiceMock.byIp(Mockito.any())).thenReturn(new Location(null, Country.USA, null, 0));
        Mockito.when(localizationServiceMock.locale(Country.USA)).thenReturn("Welcome");
        MessageSender messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        Assertions.assertEquals("Welcome", messageSender.send(Mockito.anyMap()));
    }


}