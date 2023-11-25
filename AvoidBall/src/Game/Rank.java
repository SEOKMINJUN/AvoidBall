package Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class _Rank{
    String name;
    int point;

    public _Rank(String n, int p){name=n;point=p;}

    void setName(String n){name=n;}
    String getName(){return name;}
    void setPoint(int p){point=p;}
    int getPoint(){return point;}
}

public class Rank {
    public _Rank[] easy_rank;
    public int easy_rank_count;
    public _Rank[] hard_rank;
    public int hard_rank_count;

    public Rank(){
        readRankFile();
    }

    public void readRankFile(){
        _load_easy_rank();
        _load_hard_rank();
    }

    public void _load_easy_rank(){
        File easy_rank_file = new File("easy_rank.json");
        String file_string = "";

        easy_rank_count = 0;
        easy_rank = new _Rank[10];

        if(!easy_rank_file.exists() && !easy_rank_file.isFile()){
            System.out.println("[ERROR] File not exists, Not load.");
            return;
        }
        try (Scanner file_scanner = new Scanner(easy_rank_file)) {
            while(file_scanner.hasNext()){
                file_string += file_scanner.next();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] temp = file_string.split("/");
        for(int i=0;i<temp.length;i++){
            String[] content = temp[i].split(":");
            if(content.length != 2){
                System.out.println("[ERROR] Invalid easy rank file, Not load.");
                return;
            }
            if(easy_rank_count < 10)
                easy_rank[easy_rank_count++] = new _Rank(content[0],Integer.parseInt(content[1]));
            else
                break;
        }
        System.out.printf("Load rank %d\n", easy_rank_count);
    }

    public void _load_hard_rank(){
        File hard_rank_file = new File("hard_rank.json");
        String file_string = "";
        hard_rank_count = 0;
        hard_rank = new _Rank[10];

        if(!hard_rank_file.exists() && !hard_rank_file.isFile()){
            System.out.println("[ERROR] File not exists, Not load.");
            return;
        }
        try (Scanner file_scanner = new Scanner(hard_rank_file)) {
            while(file_scanner.hasNext()){
                file_string += file_scanner.next();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] temp = file_string.split("/");
        for(int i=0;i<temp.length;i++){
            String[] content = temp[i].split(":");
            if(content.length != 2){
                System.out.println("[ERROR] Invalid hard rank file, Not load.");
                return;
            }
            if(hard_rank_count < 10)
                hard_rank[hard_rank_count++] = new _Rank(content[0],Integer.parseInt(content[1]));
            else
                break;
        }
    }

    public void saveRankFile(){
        _save_easy_rank();
        _save_hard_rank();
    }

    public void _save_easy_rank(){
        if(easy_rank_count < 0)
            return;
        try{
            String file_content = "";
            File easy_rank_file = new File("easy_rank.json");
            if(!easy_rank_file.exists()){
                easy_rank_file.createNewFile();
            }
            
            file_content += easy_rank[0].getName()+":";
            file_content += Integer.toString(easy_rank[0].getPoint());
            for(int i=1;i<easy_rank_count;i++){
                System.out.printf("[DEBUG] Max : %d , cur : %d",easy_rank_count,i);
                file_content += "/";
                file_content += easy_rank[i].getName()+":";
                file_content += Integer.toString(easy_rank[i].getPoint());
            }

            FileWriter fw = new FileWriter(easy_rank_file.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(file_content);
            bw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void _save_hard_rank(){
        if(hard_rank_count <= 0)
            return;
        try{
            String file_content = "";
            File hard_rank_file = new File("hard_rank.json");
            if(!hard_rank_file.exists()){
                hard_rank_file.createNewFile();
            }
            file_content += hard_rank[0].getName()+":";
            file_content += Integer.toString(hard_rank[0].getPoint());
            for(int i=1;i<hard_rank_count;i++){
                System.out.printf("[DEBUG] Max : %d , cur : %d",easy_rank_count,i);
                file_content += "/";
                file_content += hard_rank[i].getName()+":";
                file_content += Integer.toString(hard_rank[i].getPoint());
            }

            FileWriter fw = new FileWriter(hard_rank_file.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(file_content);
            bw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean addEasyRank(String name, int point){
        for(int i=0;i<easy_rank_count;i++){
            if(easy_rank[i].point < point){
                for(int j=(easy_rank_count>9?9:easy_rank_count);i<j;j--){
                    System.out.printf("PUSH / MAX : %d / I : %d / CUR : %d <- %d\n",easy_rank_count,i,j,j-1);
                    easy_rank[j] = easy_rank[j-1];
                }
                easy_rank[i] = new _Rank(name, point);
                if(easy_rank_count < 10)
                    easy_rank_count++;
                return true;
            }
        }
        if(easy_rank_count < 10){
            easy_rank[easy_rank_count++] = new _Rank(name, point);
            return true;
        }
        return false;
    }

    public boolean addHardRank(String name, int point){
        for(int i=0;i<hard_rank_count;i++){
            if(hard_rank[i].point < point){
                for(int j=(hard_rank_count>9?9:hard_rank_count);i<j;j--){
                    System.out.printf("PUSH / MAX : %d / I : %d / CUR : %d <- %d\n",hard_rank_count,i,j,j-1);
                    hard_rank[j] = hard_rank[j-1];
                }
                hard_rank[i] = new _Rank(name, point);
                if(hard_rank_count < 10)
                    hard_rank_count++;
                return true;
            }
        }
        if(hard_rank_count < 10){
            hard_rank[hard_rank_count++] = new _Rank(name, point);
            return true;
        }
        return false;
    }
}

