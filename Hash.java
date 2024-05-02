import java.io.*;
import java.util.*;
public class Hash {
        public static void main(String[] args) throws IOException, ClassNotFoundException {
            HashMap<String , List<Integer>>hashMap=new HashMap<>();
            File hashFile=new File("hash.bmp");
            if(hashFile.exists()){
                ObjectInputStream getHash=new ObjectInputStream(new FileInputStream(hashFile));
                hashMap=(HashMap<String, List<Integer>>)getHash.readObject();
                getHash.close();
            }
            Scanner in=new Scanner(System.in);
            System.out.println("@为文件输入结束标志,#为输入结束标志!\n请输入句子:>");
            int flag=0;
            while (!in.hasNext("#")){
                File file=new File("word"+flag+".txt");
                if(!file.exists()) {
                    if(!file.createNewFile())System.exit(1);
                }
                while (!in.hasNext("@")&&!in.hasNext("#")){
                    String temp=in.nextLine();
                    PrintWriter printWriter=new PrintWriter(new FileOutputStream(file,true));
                    printWriter.println(temp);
                    printWriter.flush();
                    String []words=temp.split("[ |.]");
                    for (String word:words) {
                        word=word.toLowerCase();
                        if(hashMap.containsKey(word)){
                            hashMap.get(word).add(flag);
                        }
                        else{
                            hashMap.putIfAbsent(word,new ArrayList<>());
                            hashMap.get(word).add(flag);
                        }
                    }
                    printWriter.close();
                }
                if(!in.hasNext("#"))in.nextLine();
                flag++;
            }
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(hashFile));
            objectOutputStream.writeObject(hashMap);
            objectOutputStream.close();
            in.nextLine();
            while (true){
                System.out.println("请输入要查找单词:");
                String word=in.next();
                List list_temp=null;
                if(hashMap.containsKey(word)) {
                    list_temp = hashMap.get(word);
                }
                if(list_temp==null){
                    System.out.println("未在文件中找到该单词!");
                }else{
                    System.out.print("存在"+word+"的文件ID:");
                    list_temp.forEach((v)->{
                        System.out.print(v+" ");
                    });
                    System.out.println();
                }

            }
        }
}
