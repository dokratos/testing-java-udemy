import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProductServiceTest {
    @BeforeAll
    static void setup() {
        System.out.println("Test methods related to User products");
    }

    @Test
    void testCreateProduct_whenProductTitleIsMissing_throwsProductServiceException() {
    }
}
