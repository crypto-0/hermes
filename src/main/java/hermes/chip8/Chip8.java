package hermes.chip8;
import hermes.shared.*;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Chip8 extends GameSystem implements KeyListener{
  private Chip8Cpu cpu;
  private Ram ram;
  private Chip8PPU PPU;
  private Timer delayTimer;
  private Timer soundTimer;
  private Chip8Bus bus;
  private boolean paused = false;
  private boolean step = false;
  private final int MAXCYCLES =9;
  private int elapsecycles = 0;
  public Chip8(){
    cpu = new Chip8Cpu();
    ram = new Ram(4000);
    PPU = new Chip8PPU(12);
    delayTimer = new Timer(4);
    soundTimer = new Timer(8);
    bus = new Chip8Bus(cpu, ram, delayTimer, soundTimer, PPU);
    cpu.loadFonts();
    cpu.reset();
    setLayout(new GridLayout(2,2));
    add(PPU,1,0);
    add(cpu);
    add(ram);
    addKeyListener(this);
  }

	@Override
	public void update() {
    if(!paused){
      for(int a =0; a < MAXCYCLES; a++) cpu.clock();
      PPU.clock();
      delayTimer.clock();
      soundTimer.clock();
    }
    else{
      if(step){
        step = false;
        cpu.clock();
        elapsecycles++;
        if(elapsecycles == MAXCYCLES){
          PPU.clock();
          delayTimer.clock();
          soundTimer.clock();
          elapsecycles = 0;
        }
      }
    }
		
	}

	@Override
	public void render() {
    cpu.render();
    ram.render();
    PPU.render();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
    switch(arg0.getKeyCode()){
      case 80:
        paused = !paused;
        break;
      case 78:
        step = true;
        break;
    }
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void loadRom(String filepath) {
    try{
      FileInputStream fileInputStream = new FileInputStream(filepath);
      File file = new File(filepath);
      int singleByte;
      int currentByteIndex = 0;
      short rom[] = new short[(int)file.length()];
      while((singleByte = fileInputStream.read()) != -1){
        rom[currentByteIndex++] = (short)singleByte;
      }
      fileInputStream.close();
      cpu.loadRom(rom);
    }
    catch(IOException e){
    }

	}
}