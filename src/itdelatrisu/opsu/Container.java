/*
 * opsu! - an open-source osu! client
 * Copyright (C) 2014, 2015 Jeffrey Han
 *
 * opsu! is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * opsu! is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with opsu!.  If not, see <http://www.gnu.org/licenses/>.
 */

package itdelatrisu.opsu;

import fluddokt.opsu.fake.*;
import itdelatrisu.opsu.audio.MusicController;
import itdelatrisu.opsu.states.Options;

//import org.newdawn.slick.AppGameContainer;
//import org.newdawn.slick.Game;
//import org.newdawn.slick.SlickException;

/**
 * AppGameContainer extension that sends critical errors to ErrorHandler.
 */
public class Container extends AppGameContainer {
	/**
	 * SlickException causing game failure.
	 */
	protected SlickException e = null;

	/**
	 * Create a new container wrapping a game
	 * 
	 * @param game The game to be wrapped
	 * @throws SlickException Indicates a failure to initialise the display
	 */
	public Container(Game game) throws SlickException {
		super(game);
	}

	/**
	 * Create a new container wrapping a game
	 * 
	 * @param game The game to be wrapped
	 * @param width The width of the display required
	 * @param height The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 * @throws SlickException Indicates a failure to initialise the display
	 */
	public Container(Game game, int width, int height, boolean fullscreen) throws SlickException {
		super(game);
	}

	@Override
	public void start() throws SlickException {
		try {
			setup();
			getDelta();
			while (running())
				gameLoop();
		} finally {
			MusicController.reset();  // prevent loading tracks from re-initializing OpenAL
			destroy();
			if (e != null) {
				ErrorHandler.error(null, e, true);
				e = null;
			}
		}

		if (forceExit)
			System.exit(0);
	}

	@Override
	protected void updateAndRender(int delta) throws SlickException {
		try {
			super.updateAndRender(delta);
		} catch (SlickException e) {
			this.e = e;
			throw e;
		}
	}

	@Override
	public void exit() {
		Options.saveOptions();
		Opsu.closeSocket();
		running = false;
	}
}
