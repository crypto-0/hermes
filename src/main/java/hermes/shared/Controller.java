package hermes.shared;

public interface Controller{
  public abstract short read(int ioAddress);
  public abstract void write(int ioAddress,short data);
  public abstract void clock();
  public abstract int getBaseAddress();
}