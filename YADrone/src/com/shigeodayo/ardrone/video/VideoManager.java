/*
 *
  Copyright (c) <2011>, <Shigeo Yoshida>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
The names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.shigeodayo.ardrone.video;

import java.net.InetAddress;

import com.shigeodayo.ardrone.command.CommandManager;
import com.shigeodayo.ardrone.command.VideoBitRateMode;
import com.shigeodayo.ardrone.manager.AbstractTCPManager;
import com.shigeodayo.ardrone.utils.ARDroneUtils;

public class VideoManager extends AbstractTCPManager {
	private VideoDecoder decoder;

	private CommandManager manager = null;

	private ImageListener listener = null;

	public VideoManager(InetAddress inetaddr, CommandManager manager, VideoDecoder decoder) {
		super(inetaddr);
		this.manager = manager;
		this.decoder = decoder;
	}

	public void setImageListener(ImageListener listener) {
		this.listener = listener;
	}

	public boolean connect(int port) {
		if (decoder == null)
			return false;

		return super.connect(port);
	}

	@Override
	public void run() {
		if (decoder == null)
			return;
		connect(ARDroneUtils.VIDEO_PORT);
		ticklePort(ARDroneUtils.VIDEO_PORT);
		manager.setVideoData(true);
		ticklePort(ARDroneUtils.VIDEO_PORT);
		manager.setVideoBitrateControl(VideoBitRateMode.DISABLED);
		decoder.decode(getInputStream(), listener);
		close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shigeodayo.ardrone.manager.AbstractTCPManager#close()
	 */
	@Override
	public void close() {
		if (decoder == null)
			return;

		super.close();
	}

}
