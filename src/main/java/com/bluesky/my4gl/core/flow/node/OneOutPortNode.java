package com.bluesky.my4gl.core.flow.node;

import com.bluesky.my4gl.core.flow.port.Port;

public interface OneOutPortNode {
  Port getOutPort();
  void connect(OneInPortNode target);
  void connect(Port target);
}
