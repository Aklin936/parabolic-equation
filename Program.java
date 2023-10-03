public class Program {
    public static void main(String[] args){
        Task task = new Task();
        task.init();
        while (task.step()){}
        task.save_graph();
    }
}
