package org.jvnet.hudson.plugins;

import hudson.model.AbstractBuild;
import hudson.model.User;
import hudson.scm.ChangeLogSet;
import hudson.scm.EditType;
import org.jvnet.hudson.plugins.VaultSCMChangeLogSet;
import org.jvnet.hudson.plugins.VaultSCMChangeLogSet.VaultSCMChangeLogSetEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.kohsuke.stapler.export.Exported;

public final class VaultSCMChangeLogSet extends ChangeLogSet<VaultSCMChangeLogSetEntry>
{
	protected VaultSCMChangeLogSet(AbstractBuild<?, ?> build) {		
		super(build);
		changes = new ArrayList<VaultSCMChangeLogSetEntry>();		
	}

	public Iterator<VaultSCMChangeLogSetEntry> iterator() {
		return changes.iterator();
	}

	@Override
	public boolean isEmptySet() {
		return changes.isEmpty();
	}	
	
	public boolean addEntry(VaultSCMChangeLogSetEntry e){
		return changes.add(e);
	}
	
	private Collection<VaultSCMChangeLogSetEntry> changes;


	public static class VaultSCMChangeLogSetEntry extends ChangeLogSet.Entry{
	
		public VaultSCMChangeLogSetEntry(String name, String comment, String version, String action, String date,ChangeLogSet parent, String userName ){
			this.affectedFile = name;
			this.comment = comment;
			this.version = version;
			this.action = action;
			this.date = date;
			this.user = User.get(userName);
			setParent(parent);
		}
		
		public VaultSCMChangeLogSetEntry()
		{		
		}
		
		
		@Override
		public String getMsg() {
			return "File: ".concat(affectedFile).concat(" Action: ").concat(action).concat(" Version: ").concat(version).concat(" Comment: ").concat(comment);
		}
		
		@Override
		public String getMsgAnnotated() {
			return affectedFile;
		}
			
		public String getVersion(){
			return version;
		}
		
		public String getName(){
			return affectedFile;
		}
		
		public String getAffectedFile(){
			return affectedFile;
		}
		
		public String getAction(){
			return action;
		}
		public String getComment(){
			return comment;
		}	
		
		@Override
		public Collection<String> getAffectedPaths() {
			Collection<String> col = new ArrayList<String>();
			col.add(affectedFile);
			return col;
		}
		
		@Override
		public User getAuthor() {
			if (user == null)
				return User.getUnknown();
			return user;
		}
		
		
		@Exported
		public EditType getEditType() {
		    if (action.equalsIgnoreCase("delete")) {
		        return EditType.DELETE;
		    }
		    if (action.equalsIgnoreCase("add")) {
		        return EditType.ADD;
		    }
		    return EditType.EDIT;
		}
		
		@Exported
		String getPath(){
			return affectedFile;
		}
		
		private String comment;
		String affectedFile;
		String version;
		String date;
		private User user;
		private String action; //default is edit	
			
	}
}
