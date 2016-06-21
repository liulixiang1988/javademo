package liulixiang1988.model;

/**
 * Created by liulixiang on 16/6/15.
 */
public class Dish {
    private final String name;
    private final boolean vagetarian;
    private final int calogries;
    private final Type type;

    public Dish(String name, boolean vagetarian, int calogries, Type type) {
        this.name = name;
        this.vagetarian = vagetarian;
        this.calogries = calogries;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVagetarian() {
        return vagetarian;
    }

    public int getCalogries() {
        return calogries;
    }

    public Type getType() {
        return type;
    }

    public enum Type {MEAT, FISH, OTHER}

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", vagetarian=" + vagetarian +
                ", calogries=" + calogries +
                ", type=" + type +
                '}';
    }
}
