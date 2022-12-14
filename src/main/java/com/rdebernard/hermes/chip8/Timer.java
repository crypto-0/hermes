package com.rdebernard.hermes.chip8;
import com.rdebernard.hermes.shared.*;

public class Timer implements Controller {
  private short data;
  private final int baseAddress;
  public Timer(int baseAddress){
    this.baseAddress = baseAddress;
  }

@Override
public short read(int ioAddress) {
  if(ioAddress - baseAddress == 0) return data;
	return 0;
}

@Override
public void write(int ioAddress, short data) {
  if(ioAddress - baseAddress == 0) this.data = data;
	
}

@Override
public void clock() {
  if(data >0) data -=data;
}

@Override
public int getBaseAddress() {
	return this.baseAddress;
}

@Override
public void connectBus(Bus bus) {
	
}

@Override
public void reset() {
  data = 0;
}
}

