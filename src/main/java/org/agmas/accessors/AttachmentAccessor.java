package org.agmas.accessors;

import org.agmas.porting.AttachmentWithValue;
import org.agmas.porting.PPAttachment;

import java.util.ArrayList;
import java.util.HashMap;

public interface AttachmentAccessor {

    HashMap<PPAttachment<?>, AttachmentWithValue<?>> perky$attachments();
}
