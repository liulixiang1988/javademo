package liulx.FilterDemo.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by liulixiang on 2015/4/22.
 * Description:
 */
@WebServlet(name = "AsyncServlet", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("ҵ��ʼ�¼���"+new Date());
        AsyncContext context = request.startAsync();
        new Thread(new Executor(context)).start();
        System.out.println("ҵ������¼���" + new Date());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public class Executor implements Runnable{

        AsyncContext context;
        public Executor(AsyncContext context){
            this.context = context;
        }
        @Override
        public void run() {
            //ִ����ظ���ҵ��
            try {
                Thread.sleep(1000*10);
                System.out.println("ҵ������¼���"+new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
