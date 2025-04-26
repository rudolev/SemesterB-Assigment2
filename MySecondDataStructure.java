
public class MySecondDataStructure {
    private final int numerOfQualities = 6;
    private int[] currentRaisePerQuality;
    private int[] qualityCounters;
    private Product[] mostExpensiveOfEachQuality;
    private int size;
    private int sumOfQuality; //for averge
    private MyArray<Product> productsArray;

    /***
     * This function is the Init function.
     * @param N The maximum number of elements in the data structure at each time.
     */
    public MySecondDataStructure(int N) {
        currentRaisePerQuality = new int[numerOfQualities];
        qualityCounters = new int[numerOfQualities];
        mostExpensiveOfEachQuality = new Product[numerOfQualities];
        size = 0;
        sumOfQuality = 0;
        productsArray = new MyArray<Product>(N);
    }

    public void insert(Product product) {
        int prodQuality = product.quality();
        int amountRaisedSoFar = currentRaisePerQuality[prodQuality];
        product.setPricePrevRaise(amountRaisedSoFar);

        Element<Product> elementToAdd = new Element<>(product.id(), product);
        productsArray.insert(new ArrayElement<>(elementToAdd));//insert

        if (mostExpensiveOfEachQuality[prodQuality] != null) {
            Product mostExpensive = mostExpensiveOfEachQuality[prodQuality];
            if (product.price() > calcCurrentPrice(mostExpensive))
                mostExpensiveOfEachQuality[prodQuality] = product;
        }
        else
            mostExpensiveOfEachQuality[prodQuality] = product;

        qualityCounters[prodQuality]++;
        size++;
        sumOfQuality += prodQuality;
    }

    private int calcCurrentPrice(Product product) {
        return product.price() + currentRaisePerQuality[product.quality()] - product.getPricePrevRaise();
    }

    private Product findMaximum(int quality) {
        int maxPrice = -1;
        Product maxPriceProduct = null;
        for (int i = 0; i < productsArray.size(); i++) {
            Product currentProduct = productsArray.get(i).satelliteData();
            if (currentProduct.quality() == quality) {
                int currentPrice = calcCurrentPrice(currentProduct);
                if (currentPrice > maxPrice) {
                    maxPrice = currentPrice;
                    maxPriceProduct = currentProduct;
                }
            }
        }

        return maxPriceProduct;
    }

    public void findAndRemove(int id) {
        ArrayElement<Product> toRemove = productsArray.search(id);

        if (toRemove == null)
            return;

        productsArray.delete(toRemove);
        size = size - 1;

        int toRemoveQuality = toRemove.satelliteData().quality();

        sumOfQuality = sumOfQuality - toRemoveQuality;
        qualityCounters[toRemoveQuality]--;

        if (toRemove.satelliteData().equals(mostExpensiveOfEachQuality[toRemoveQuality])) {
            if (qualityCounters[toRemove.satelliteData().quality()] == 0)
                mostExpensiveOfEachQuality[toRemoveQuality] = null;
            else
                mostExpensiveOfEachQuality[toRemoveQuality] = findMaximum(toRemove.satelliteData().quality());
        }
    }

    public int medianQuality() {
        if (size == 0)
            return -1;

        int median = size / 2;
        int i = 0;

        if (size % 2 != 0)
            median++;

        while (median > 0) {
            median = median - qualityCounters[i];
            i++;
        }
        return i - 1;
    }

    public double avgQuality() {
        if (size == 0)
            return -1;
        return (double) sumOfQuality / size;
    }

    public void raisePrice(int raise, int quality) {
        currentRaisePerQuality[quality] += raise;
    }

    public Product mostExpensive() {
        if (size == 0)
            return null;

        int maxPrice = -1;
        Product maxProduct = null;

        for (int i = 0; i < numerOfQualities; i++) {
            if (mostExpensiveOfEachQuality[i] != null) {
                Product currentMostExpensive = mostExpensiveOfEachQuality[i];
                int price = currentMostExpensive.price() + (currentRaisePerQuality[i] - currentMostExpensive.getPricePrevRaise());
                if (maxPrice < price) {
                    maxPrice = price;
                    maxProduct = currentMostExpensive;
                }
            }
        }
        return maxProduct;
    }

}
