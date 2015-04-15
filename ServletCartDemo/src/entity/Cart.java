package entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by liulixiang on 2015/4/15.
 * Description:���ﳵ��
 */
public class Cart {
    //������Ʒ�ļ���
    private HashMap<Items, Integer> goods;

    //���ﳵ���ܽ��
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

    //�����Ʒ�����ﳵ
    public boolean addGoodsInCart(Items item, int number){
        goods.put(item, number);
        calTotalPrice();
        return true;
    }

    //ɾ����Ʒ
    public boolean removeGoodsFromCart(Items item){
        goods.remove(item);
        calTotalPrice();
        return true;
    }

    //ͳ�ƹ��ﳵ���ܽ��
    public double calTotalPrice(){
        double sum = 0.0;
        Set<Items> keys = goods.keySet();//��ü��ļ���
        Iterator<Items> it = keys.iterator(); //��õ���������
        while(it.hasNext()){
            Items i = it.next();
            sum += i.getPrice()*goods.get(i);
        }
        setTotalPrice(sum);
        return sum;
    }
}
