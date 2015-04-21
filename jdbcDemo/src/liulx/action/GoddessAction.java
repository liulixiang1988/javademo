package liulx.action;

import liulx.dao.GoddessDao;
import liulx.model.Goddess;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by liulixiang on 2015/4/21.
 * Description:
 */
public class GoddessAction {
    public static void main(String[] args) throws SQLException {
        GoddessDao g = new GoddessDao();
        List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name", "user_name");
        param.put("rela", "=");
        param.put("value", "'–°√¿'");
        params.add(param);
        List<Goddess> result=g.query(params);
        for(int i = 0; i<result.size(); i++){
            System.out.println(result.get(i).toString());
        }
//
//        Goddess g1 = new Goddess();
//
//        g1.setUser_name("–°œƒ");
//        g1.setAge(22);
//        g1.setSex(1);
//        g1.setBirthday(new Date());
//        g1.setEmail("xiaoxia@imooc.com");
//        g1.setMobile("18786868686");
//        g1.setCreate_user("ADMIN");
//        g1.setUpdate_user("ADMIN");
//        g1.setIsDel(1);

        //g.addGoddess(g1);

//        List<Goddess> gs = g.query();
//
//        for(Goddess goddess: gs){
//            System.out.println(goddess.getUser_name()+","+goddess.getAge());
//        }

//        Goddess g2 = g.get(3);
//        g2.setAge(23);
//        g.updateGoddess(g2);
//
//        g.delGoddess(g2.getId());
    }
}
