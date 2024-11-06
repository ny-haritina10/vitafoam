package mg.itu.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mg.itu.model.Product;
import mg.itu.model.ProductTransformation;
import mg.itu.model.SpongeTransformation;

public class ProductService {
    
    public static double getVolume(Product product) 
    { return product.getDimHeight() * product.getDimLength() * product.getDimWidth(); }

    public static Map<Integer, Double> mapProductQuantities(Product[] products, HttpServletRequest req) {
        Map<Integer, Double> productQuantities = new HashMap<>();
        for (Product product : products) {
            String param = "productQuantity_" + product.getId();
            if (req.getParameter(param) != null && !req.getParameter(param).isEmpty()) {
                double quantity = Double.parseDouble(req.getParameter(param));
                System.out.println("PRD: " + product.getLabel() + " QTE" + quantity);
                productQuantities.put(product.getId(), quantity);
            }
        }

        return productQuantities;
    }

    public static void insertProductQuantities(Map<Integer, Double> productQuantities, SpongeTransformation lastSpongeTransformation) 
        throws Exception
    {
        for (Map.Entry<Integer, Double> entry : productQuantities.entrySet()) {
            ProductTransformation productTransformation = new ProductTransformation();

            productTransformation.setSpongeTransformation(lastSpongeTransformation);
            productTransformation.setProduct(new Product().getById(entry.getKey(), Product.class, null));
            productTransformation.setQuantity(entry.getValue().intValue());

            productTransformation.insert(null);
        }
    }

    public static double getMappedProductsTotalVolume(Map<Integer, Double> productQuantities) 
        throws Exception
    {
        double sum = 0;
        for (Map.Entry<Integer, Double> entry : productQuantities.entrySet()) {
            Product product = new Product().getById(entry.getKey(), Product.class, null);
            sum += (getVolume(product) * entry.getValue());
        }

        return sum;
    }
}