package com.meetu.community.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meetu.community.domain.Activity;
import com.meetu.community.domain.ComBiu;
import com.meetu.community.domain.Notify;
import com.meetu.community.domain.Post;
import com.meetu.community.domain.Praise;
import com.meetu.community.domain.Report;
import com.meetu.community.domain.Tags;
import com.meetu.community.service.ActivityService;
import com.meetu.community.service.ComBiuService;
import com.meetu.community.service.NotifyService;
import com.meetu.community.service.PostService;
import com.meetu.community.service.PraiseService;
import com.meetu.community.service.ReportService;
import com.meetu.community.service.TagsService;

@Controller
@RequestMapping("test")
public class TestController {
	
	@Autowired
	private TagsService tagsService;
	
	@Autowired
	private PostService postserice;
	
	@Autowired
	private PraiseService praiseService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private ComBiuService biuService;
	
	@Autowired
	private ActivityService activityService;
	
	@RequestMapping(value="run",method=RequestMethod.POST)
	public ResponseEntity<Post> run(@RequestParam("content")String content,@RequestParam("tagId")Integer tagId) throws Exception{
		Post post = new Post();
		post.setCommentNum(0);
		post.setContent(content);
		post.setCreateAt(new Timestamp(System.currentTimeMillis()));
		post.setCreateBy(12880);
		post.setImgs("");
		post.setLevel(0);
		post.setMusic("");
		post.setPraiseNum(0);
		post.setType(1);
//		this.postserice.savePost(post,tagId)content;
		return ResponseEntity.ok(post);
	}
	
	@RequestMapping(value="run2",method=RequestMethod.POST)
	public ResponseEntity<Tags> run2() throws Exception{
		Tags tags = this.tagsService.selectTagsById(2);
		tags.setLevel(1);
		tags.setPostNum(tags.getPostNum()+1);
		this.tagsService.updateTag(tags);
		return ResponseEntity.ok(tags);
	}
	
	@RequestMapping(value="run3",method=RequestMethod.POST)
	public ResponseEntity<Praise> run3() throws Exception{
		Praise praise = new Praise();
		praise.setCreateAt(new Timestamp(System.currentTimeMillis()));
		praise.setPostId(1);
		praise.setUserFrom(12345);
		praise.setUserTo(54321);
//		this.praiseService.insertPraise(praise);
		return ResponseEntity.ok(praise);
	}
	
	@RequestMapping(value="run4",method=RequestMethod.POST)
	public ResponseEntity<Report> run4() throws Exception{
		Report report = new Report();
		report.setCommentId(123);
		report.setCreateAt(new Timestamp(System.currentTimeMillis()));
		report.setPostId(12);
		report.setUserFrom(1234);
		report.setUserTo(4321);
		this.reportService.insertReport(report);
		return ResponseEntity.ok(report);
	}
	
	@RequestMapping(value="run5",method=RequestMethod.POST)
	public ResponseEntity<Notify> run5() throws Exception{
		Notify notify = new Notify();
		notify.setComment("被点赞了");
		notify.setContent("具体的评论");
		notify.setCreateAt(new Timestamp(System.currentTimeMillis()));
		notify.setImgs("");
		notify.setIsRead(0);
		notify.setPostId(12);
		notify.setType(1);
		notify.setUserFrom(12880);
		this.notifyService.insertNotify(notify);
		return ResponseEntity.ok(notify);
	}
	@RequestMapping(value="run6",method=RequestMethod.POST)
	public ResponseEntity<Integer> run6() throws Exception{
//		List<Notify> list = this.notifyService.selectNotifyByUserTo(12880);
//		return ResponseEntity.ok(list.size());
		return null;
	}
	
	@RequestMapping(value="run7",method=RequestMethod.POST)
	public ResponseEntity<Void> run7() throws Exception{
		this.notifyService.setNotifyRead(12880);
		return ResponseEntity.ok(null);
	}
	
	@RequestMapping(value="run8",method=RequestMethod.POST)
	public ResponseEntity<ComBiu> run8() throws Exception{
		ComBiu biu = new ComBiu();
		biu.setCreateAt(new Timestamp(System.currentTimeMillis()));
		biu.setIsRead(0);
		biu.setStatus(0);
		biu.setUserCodeGrab(12334);
		biu.setUserCodeMine(12880);
		this.biuService.insertBiu(biu);
		return ResponseEntity.ok(biu);
	}
//	@RequestMapping(value="run9",method=RequestMethod.POST)
//	public ResponseEntity<Integer> run9() throws Exception{
//		List<Biu> list = this.biuService.selectBiuByUserCode(12880);
//		return ResponseEntity.ok(list.size());
//	}
	@RequestMapping(value="run10",method=RequestMethod.POST)
	public ResponseEntity<Void> run10() throws Exception{
		this.biuService.setBiuRead(12880);
		return ResponseEntity.ok(null);
	}
	@RequestMapping(value="run11",method=RequestMethod.POST)
	public ResponseEntity<Void> run11() throws Exception{
		ComBiu biu = new ComBiu();
		biu.setUserCodeMine(12880);
		biu.setUserCodeGrab(12334);
		biu.setStatus(1);
		this.biuService.acceptBiu(biu);
		return ResponseEntity.ok(null);
	}
	@RequestMapping(value="run12",method=RequestMethod.POST)
	public ResponseEntity<Integer> run12() throws Exception{
		Integer num = this.biuService.selectBiuUnRead(12880);
		System.out.println(num);
		return ResponseEntity.ok(num);
	}
	
	@RequestMapping(value="run13",method=RequestMethod.POST)
	public ResponseEntity<Activity> run13() throws Exception{
		Activity activity = new Activity();
		activity.setCover("封面");
		activity.setCreateAt(new Timestamp(System.currentTimeMillis()));
		activity.setIndex(0);
		activity.setTitle("标题");
		activity.setType(0);
		activity.setUrl("地址");
		activityService.insertActivity(activity);
		return ResponseEntity.ok(activity);
	}
	@RequestMapping(value="run14",method=RequestMethod.POST)
	public ResponseEntity<List<Activity>> run14() throws Exception{
		List<Activity> list = this.activityService.selectActivityByType(0);
		return ResponseEntity.ok(list);
	}
	
}
