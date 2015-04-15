package entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by liulixiang on 2015/4/15.
 * Description:购物车类
 */
public class Cart {
    //购买商品的集合
    private HashMap<Items, Integer> goods;

    //购物车的总金额
    private double totalPrice;

    public Cart() {
        goods = new HashMap<Items, Integer>();
        totalPrice = 0.0;
    }

    public HashMap<Items, Integer> getGoods() {
        return goods;
    }

    public void setGoods(HashMap<Items, Integer> goods) {
        this.goods = goods;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    //添加商品到购物车
    public boolean addGoodsInCart(Items item, int number){
        goods.put(item, number);
        calTotalPrice();
        return true;
    }

    //删除商品
    public boolean removeGoodsFromCart(Items item){
        goods.remove(item);
        calTotalPrice();
        return true;
    }

    //统计购物车的总金额
    public double calTotalPrice(){
        double sum = 0.0;
        Set<Items> keys = goods.keySet();//获得键的集合
        Iterator<Items> it = keys.iterator(); //获得迭代器对象
        while(it.hasNext()){
            Items i = it.next();
            sum += i.getPrice()*goods.get(i);
        }
        setTotalPrice(sum);
        return sum;
    }
}
