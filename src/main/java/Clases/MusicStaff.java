package Clases;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicStaff {

    public  void PlayMusic(String filepath){ //audi

        try{
            File musicPath=new File(filepath);
            if(musicPath.exists())
            {
                AudioInputStream audioInput=AudioSystem.getAudioInputStream(musicPath);
                Clip clip=AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            }else{
                System.out.println("No encontro archivo [Musica]");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public void Effect(String filepath)
    {
        try{
            File musicPath=new File(filepath);
            if(musicPath.exists())
            {
                AudioInputStream audioInput= AudioSystem.getAudioInputStream(musicPath);
                Clip clip=AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

            }else{
                System.out.println("No encontro archivo");
            }

        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
