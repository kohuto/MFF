package cz.cuni.mff.kohut.java;

import java.io.BufferedReader;
import java.io.IOException;

public class FileProcessor {
    int pocet_uzivatelu;
    int pocet_uzivatelu_s_UID_0;
    int pocet_uzivatelu_s_nastavenym_shellem_bin_bash;
    int pocet_uzivatelu_s_prazdnou_polozkou_comment;

    public FileProcessor(BufferedReader br) {
        this.br = br;
    }

    BufferedReader br;
    String st;
    private void processLine(String st){
        String[] temp = st.split("\\:");
        pocet_uzivatelu++;
        if(temp[2].equals("0"))
            pocet_uzivatelu_s_UID_0++;
        if(temp[6].equals("/sbin/nologin"))
            pocet_uzivatelu_s_nastavenym_shellem_bin_bash++;
        if(temp[4].equals(""))
            pocet_uzivatelu_s_prazdnou_polozkou_comment++;
    }
    public void processFile() {
        while (true) {
            try {
                if (!((st = br.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            processLine(st);
        }
    }
    public void printData(){
        System.out.println("Users: "+pocet_uzivatelu);
        System.out.println("Users with UID 0: "+pocet_uzivatelu_s_UID_0);
        System.out.println("Users with /sbin/nologin shell: "+pocet_uzivatelu_s_nastavenym_shellem_bin_bash);
        System.out.println("Users with empty comment field: "+pocet_uzivatelu_s_prazdnou_polozkou_comment);
    }
}
