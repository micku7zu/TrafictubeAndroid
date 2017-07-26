package com.micutu.trafictube.Repository;

import android.content.Context;

import java.util.List;

public interface VotesRepository {
    public List<Integer> alreadyVoted(List<Integer> postIds);
    public Boolean alreadyVoted(Integer postId);
    public void vote(Integer postId);
}
