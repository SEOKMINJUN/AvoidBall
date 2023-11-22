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
        RankFileRead();
    }

    public void RankFileRead(){
        _get_easy_rank();
        _get_hard_rank();
    }

    public void _get_easy_rank(){
        File easy_rank_file = new File("easy_rank.json");
        String file_string = "";
        if(!easy_rank_file.exists() && !easy_rank_file.isFile()){
            System.out.println("[ERROR] File not exists, Not load.");
            easy_rank_count = 0;
            easy_rank = new _Rank[256];
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
                easy_rank_count = 0;
                easy_rank = new _Rank[256];
                return;
            }
            easy_rank[easy_rank_count++] = new _Rank(content[0],Integer.parseInt(content[1]));
        }
    }

    public void _get_hard_rank(){
        File hard_rank_file = new File("hard_rank.json");
        String file_string = "";
        if(!hard_rank_file.exists() && !hard_rank_file.isFile()){
            System.out.println("[ERROR] File not exists, Not load.");
            hard_rank_count = 0;
            hard_rank = new _Rank[256];
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
                hard_rank_count = 0;
                hard_rank = new _Rank[256];
                return;
            }
            hard_rank[hard_rank_count++] = new _Rank(content[0],Integer.parseInt(content[1]));
        }
    }

    public void RankFileSave(){
        _save_easy_rank();
        _save_hard_rank();
    }

    public void _save_easy_rank(){
        try{
            String file_content = "";
            File easy_rank_file = new File("easy_rank.json");
            if(!easy_rank_file.exists()){
                easy_rank_file.createNewFile();
            }
            
            file_content += easy_rank[0].getName();
            file_content += Integer.toString(easy_rank[0].getPoint());
            for(int i=1;i<easy_rank.length;i++){
                file_content += "/";
                file_content += easy_rank[i].getName();
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
        try{
            String file_content = "";
            File hard_rank_file = new File("hard_rank.json");
            if(!hard_rank_file.exists()){
                hard_rank_file.createNewFile();
            }
            
            file_content += hard_rank[0].getName();
            file_content += Integer.toString(hard_rank[0].getPoint());
            for(int i=1;i<hard_rank.length;i++){
                file_content += "/";
                file_content += hard_rank[i].getName();
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
}

