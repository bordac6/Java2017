package demos;

class Philosopher extends Thread {
  private int identity;
  private PhilCanvas view;
  private Diners controller;
  private Fork left;
  private Fork right;

  Philosopher(Diners ctr, int id, Fork l, Fork r) {
    controller = ctr; view = ctr.display;
    identity = id; left = l; right = r;
  }

  public void run() {
    try {
      while (true) {
        //thinking
        view.setPhil(identity,PhilCanvas.THINKING);
        sleep(controller.sleepTime());
        //hungry
        view.setPhil(identity,PhilCanvas.HUNGRY);
        right.get();
        //gotright chopstick
        view.setPhil(identity,PhilCanvas.GOTRIGHT);
        sleep(200);
        left.get();
        //eating
        view.setPhil(identity,PhilCanvas.EATING);
        sleep(controller.eatTime());
        right.put();
        left.put();
      }
    } catch (java.lang.InterruptedException e) {}
  }
}
