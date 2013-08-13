package com.bluesky.visualprogramming.samplewebapp;

public class Storage {

	public String getPost(int id) {
		return "<p>this is the post...#id#</p>".replace("#id#",
				String.valueOf(id));
	}

	public String getMasterPage() {
		return "<div>header</div><div>#post#</div><div>footer</div>";
	}

}
