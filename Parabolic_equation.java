import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Parabolic_equation {
ArrayList<Double> ux, uy, yx, yy, bx, by;
ArrayList<Double> grnorm = new ArrayList<Double>();
int amount_time, amount_dots, n_step;
double start_dot, end_dot, start_time, end_time,
 len, time, h, delta, eps, lambda;
double[] dots, pos, dif;
double[][] func;

    //set variables
    public void init(){
        //create lists 
        ux = new ArrayList<Double>();
        uy = new ArrayList<Double>();

        yx = new ArrayList<Double>();
        yy = new ArrayList<Double>();

        bx = new ArrayList<Double>();
        by = new ArrayList<Double>();

        n_step = 1;
        amount_dots = 100;
        amount_time = 20000;
        time = 0.5;
        start_dot = 0;
        end_dot = 1;
        eps = 0.01;
        len = end_dot - start_dot;
        h = len / (amount_dots-1);
        delta = time / (amount_time-1);
        lambda = 5;
        func = new double[amount_time][amount_dots];
        pos = new double[amount_time];
        dif = new double[amount_time];
        System.out.println("initializing variables");
    }

    private double norm(double[] v){
        double max = 0;
        for (int i = 0; i<v.length; i++){
            if (Math.abs(v[i]) > max)
                max = Math.abs(v[i]);
        }
        return max;
    }

    private double norm2(double[] v){
        double n = 0;
        for (int i = 0; i<v.length; i++){
            n = n+Math.pow(v[i],2);
        }
        n = Math.sqrt(n);
        return n;
    }

    private void prep_u(){
        for (int t=0; t<amount_time-2; t++){
            ux.add(t*delta);
            uy.add(pos[t]);
        }
    }

    private void prep_y(){
        for (int t=0; t<amount_dots; t++){
            yx.add(t*h);
            yy.add(func[amount_time-1][t]);
        }
    }

    private void prep_b(){
        for (int t=0; t<amount_dots; t++){
            bx.add(t*h);
            by.add(t*h);
        }
    }
    
    public boolean step(){
        //System.out.println("Step number: "+n_step++);
        for (int i = 0; i < amount_dots; ++i)
        {
            func[0][i] = 0;
        }

        for (int i = 0; i < amount_time - 1; ++i)
        {
            for (int j = 1; j < amount_dots - 1; ++j)
            {
                func[i + 1][j] = (func[i][j + 1] - 2 * func[i][j] + func[i][j - 1]) / (h * h) * delta + func[i][j];
            }
            func[i + 1][amount_dots - 1] = (h * pos[i + 1] + func[i + 1][amount_dots - 2]) / (1 + h);
        }

        for (int j = 0; j < amount_dots - 1; ++j){
            func[amount_time - 1][j] = func[amount_time - 1][j] - j*h;
        }

        for (int i = amount_time - 1; i > 0; --i)
        {
            for (int j = 1; j < amount_dots - 1; ++j)
            {
                func[i - 1][j] = (func[i][j + 1] - 2 * func[i][j] + func[i][j - 1]) / (h * h) * delta + func[i][j];
            }
            func[i - 1][amount_dots - 1] = (func[i - 1][amount_dots - 2]) / (1 + h);
        }

        for (int i = 0; i < amount_time - 1; ++i)
        {
            dif[i] = 2 * func[i][amount_dots - 1];
        }

        //System.out.println("Grardient norm: "+norm(dif));
        grnorm.add(norm(dif));

        if (norm(dif)<eps){
            return false;
        }

        for (int i = 0; i < pos.length; i++){
            pos[i] = pos[i] - lambda * dif[i];
        }

        return true;
    }

    public void graph(ArrayList<Double> x, ArrayList<Double> y, String name) {
        File file = new File("/home/neil/Documents/" + name + ".out");
 
        try (PrintWriter out = new PrintWriter(file))
        {
            for (int i=0; (i<x.size())&&(i<y.size());i++){
                out.print(x.get(i));
                out.print("|");
                out.println(y.get(i));
            }
            System.out.println("Successfully created the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Double> get_ux(){
        return ux;
    }

    public ArrayList<Double> get_uy(){
        return uy;
    }

    public ArrayList<Double> get_yx(){
        return yx;
    }

    public ArrayList<Double> get_yy(){
        return yy;
    }

    public ArrayList<Double> get_by(){
        return by;
    }

    public ArrayList<Double> get_bx(){
        return bx;
    }

    public void save_graph(){
        prep_u();
        prep_y();
        prep_b();
        graph(ux, uy, "u");
        graph(yx, yy, "y");
        graph(bx, by, "b");
    }
}
