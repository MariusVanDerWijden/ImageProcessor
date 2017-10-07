package preprocessor;

/**
 * Created by matematik on 12/6/15.
 */
public class NameGenerator {

    private String[] names;
    private int i = 0;
    public NameGenerator (int nameCount){
        names = new String[nameCount];
        int w = 26;
        int chars = 1;
        while(w < nameCount){
            w = w*26;
            chars++;
        }

        for(int i = 0; i < chars; i++){

        }
    }

    public String genName(String s, int i,char ch){
        if(i == 0){
            return s;
        }
        s+=ch;
        char nextChar;
        if(ch == 'z'){
            return genName(s,i--,'a');
        }else{
            return genName(s,i--,ch++);
        }


    }

    public String getNextName(){
        return names[i++];
    }
}
