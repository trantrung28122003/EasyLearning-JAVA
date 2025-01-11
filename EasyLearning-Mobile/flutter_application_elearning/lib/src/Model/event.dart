class Event {
  final String id;
  final String eventType;
  final String eventName;
  final String dateStart;
  final String dateEnd;
  final String location;
  final String dateCreate;
  final String dateChange;
  final String changedBy;
  final bool isDeleted;
  final int totalPartsByCourseEvent;
  final int completedPartsByCourseEvent;
  final List<TrainingPart> trainingParts;
  final List<TrainingPartProgressResponse> trainingPartProgressResponses;

  Event({
    required this.id,
    required this.eventType,
    required this.eventName,
    required this.dateStart,
    required this.dateEnd,
    required this.location,
    required this.dateCreate,
    required this.dateChange,
    required this.changedBy,
    required this.isDeleted,
    required this.totalPartsByCourseEvent,
    required this.completedPartsByCourseEvent,
    required this.trainingParts,
    required this.trainingPartProgressResponses,
  });

  factory Event.fromJson(Map<String, dynamic> json) {
    var listTrainingParts = json['trainingParts'] as List;
    List<TrainingPart> trainingPartsList =
        listTrainingParts.map((i) => TrainingPart.fromJson(i)).toList();

    var listProgress = json['trainingPartProgressResponses'] as List;
    List<TrainingPartProgressResponse> progressList = listProgress
        .map((i) => TrainingPartProgressResponse.fromJson(i))
        .toList();

    return Event(
      id: json['id'],
      eventType: json['eventType'],
      eventName: json['eventName'],
      dateStart: json['dateStart'],
      dateEnd: json['dateEnd'],
      location: json['location'],
      dateCreate: json['dateCreate'],
      dateChange: json['dateChange'],
      changedBy: json['changedBy'],
      isDeleted: json['isDeleted'],
      totalPartsByCourseEvent: json['totalPartsByCourseEvent'],
      completedPartsByCourseEvent: json['completedPartsByCourseEvent'],
      trainingParts: trainingPartsList,
      trainingPartProgressResponses: progressList,
    );
  }
}

class TrainingPart {
  final String title;
  final String content;

  TrainingPart({required this.title, required this.content});

  factory TrainingPart.fromJson(Map<String, dynamic> json) {
    return TrainingPart(
      title: json['title'],
      content: json['content'],
    );
  }
}

class TrainingPartProgressResponse {
  final String partId;
  final bool isCompleted;

  TrainingPartProgressResponse(
      {required this.partId, required this.isCompleted});

  factory TrainingPartProgressResponse.fromJson(Map<String, dynamic> json) {
    return TrainingPartProgressResponse(
      partId: json['partId'],
      isCompleted: json['isCompleted'],
    );
  }
}
