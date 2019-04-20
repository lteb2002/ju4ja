package com.reremouse.ju4ja.example;

import com.reremouse.ju4ja.client.Ju4jaClient;
import com.reremouse.ju4ja.msg.JavaCallResult;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this class is used to test Ju4ja
 */
public class Ju4jaTest {

    /**
     * Class a function named as 'greetings' on Julia server
     */
    @Test
    public void testRemoteGreeting() {
        String ip = "127.0.0.1";
        int port = 6996;
        Ju4jaClient client = new Ju4jaClient(ip, port);
        Object[] as = {"Zhang fei"};
        JavaCallResult result = client.invokeFunction("greetings", "Main", as);
        if (result != null) {
            System.out.println(result.getResultStr());
            System.out.println(result.getStatus());
        }
    }

    /**
     * use Commonmath3 to solve a linear programming locally
     */
    @Test
    public void testLocalLinearProgramming() {
        /**
         * min c'x
         * s.t. Ax >= b, x>=0
         */
        double[] c = {-3, -1, -2};
        double[][] A = {
                {-1.0, -1.0, -3.0},
                {-2.0, -2.0, -5.0},
                {-4.0, -1.0, -2.0}
        };
        double[][] bigThanZero = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        double[] b = {-30, -24, -36};
        //构造目标函数
        LinearObjectiveFunction f = new LinearObjectiveFunction(c, 0);
        List<LinearConstraint> constraints = new ArrayList();
        for (int i = 0; i < b.length; i++) {
            //获得约束矩阵的第i行
            double[] row = A[i];
            double[] row2 = bigThanZero[i];
            //添加约束（大于等于）
            constraints.add(new LinearConstraint(row, Relationship.GEQ, b[i]));
            constraints.add(new LinearConstraint(row2, Relationship.GEQ, 0));
        }
        try {
            PointValuePair solution= new SimplexSolver().optimize(f, new LinearConstraintSet(constraints), GoalType.MINIMIZE);
            double[] value = solution.getPoint();
            System.out.println(Arrays.toString(value));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    /**
     * call Julia server to solve a linear programming problem
     * The function is named as 'solveDmlLp'
     */
    @Test
    public void testRemoteLinearProgramming() {
        /**
         * min c'x
         * s.t. Ax >= b, x>=0
         */
        double[] c = {-3, -1, -2};
        double[][] A = {
                {-1.0, -1.0, -3.0},
                {-2.0, -2.0, -5.0},
                {-4.0, -1.0, -2.0}
        };
        double[] b = {-30, -24, -36};
        String ip = "127.0.0.1";
        int port = 6996;
        Ju4jaClient client = new Ju4jaClient(ip, port);
        Object[] as = {c, A, b};
        JavaCallResult result = client.invokeFunction("solveDmlLp", "RereDmlLpSolver", as);
        //System.out.println(result);
        if (result != null) {
            System.out.println(result.getResultStr());
            //System.out.println(result.getStatus());
        }
    }


    /**
     * 风暴测试Julia客户端效率
     */
    @Test
    public void stormRemoteTest() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            this.testRemoteLinearProgramming();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time:" + (end - start) / 1000.0);
    }

    /**
     * 风暴测试本地效率
     */
    @Test
    public void stormLocalTest() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            this.testLocalLinearProgramming();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time:" + (end - start) / 1000.0);
    }


    public static void main(String[] args) {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}
