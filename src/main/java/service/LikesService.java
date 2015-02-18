package service;

import model.Photo;
import model.Post;

/**
 * Created by ElessarST on 18.02.2015.
 */
public interface LikesService {

    public boolean addLikePhoto(long photo);

    public boolean removeLikePhoto(long photo);

    public boolean addLikePost(long post);

    public boolean removeLikePost(long post);
}
