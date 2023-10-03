public class Program {
    public static void main(String[] args){
        Parabolic_equation task = new Parabolic_equation();
        task.init();
        while (task.step()){}
        task.save_graph();
    }
}
