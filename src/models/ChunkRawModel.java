package models;

public class ChunkRawModel {

	private int[] dataList;
	private int[] indicesList;
	
	public ChunkRawModel(int[] dataList, int[] indicesList) {
		super();
		this.dataList = dataList;
		this.indicesList = indicesList;
	}

	public int[] getDataList() {
		return dataList;
	}

	public void setDataList(int[] dataList) {
		this.dataList = dataList;
	}

	public int[] getIndicesList() {
		return indicesList;
	}

	public void setIndicesList(int[] indicesList) {
		this.indicesList = indicesList;
	}
	
}
