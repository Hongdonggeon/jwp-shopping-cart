package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }
}
