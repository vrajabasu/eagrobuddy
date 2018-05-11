export class Layout { 
  constructor(
  	public layoutId: number,
  	public layoutName: string,
  	public layoutDescription: string,
  	public activeFlag: boolean,
  	public widthX: number,
  	public heightY: number,
  	public sections: Section [] 
  ) {}	
} 

export class Section { 
	constructor(
		public sectionId: number,
		public sectionName: string,
		public sectionDescription: string,
		public activeFlag: boolean,
		public startX: number,
		public startY: number,
		public endX: number,
		public endY: number,
		public segments: Segment []
	) {}
}

export class Segment { 
	constructor(
		public segmentId: number,
		public segmentName: string,
		public segmentDescription: string,
		public activeFlag: boolean,
		public startX: number,
		public startY: number,
		public endX: number,
		public endY: number,
		public soverallThresholdstate: string
	) {}
}