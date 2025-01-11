class TrainingPart {
  final String id;
  final String trainingPartName;
  final String startTime;
  final String endTime;
  final String description;
  final String trainingPartType;
  final String imageUrl;
  final String videoUrl;
  final String courseId;
  final String courseEventId;
  final String dateCreate;
  final String dateChange;
  final String changedBy;
  final bool free;
  final bool deleted;

  TrainingPart({
    required this.id,
    required this.trainingPartName,
    required this.startTime,
    required this.endTime,
    required this.description,
    required this.trainingPartType,
    required this.imageUrl,
    required this.videoUrl,
    required this.courseId,
    required this.courseEventId,
    required this.dateCreate,
    required this.dateChange,
    required this.changedBy,
    required this.free,
    required this.deleted,
  });

  factory TrainingPart.fromJson(Map<String, dynamic> json) {
    return TrainingPart(
      id: json['id'],
      trainingPartName: json['trainingPartName'],
      startTime: json['startTime'],
      endTime: json['endTime'],
      description: json['description'] ?? '',
      trainingPartType: json['trainingPartType'],
      imageUrl: json['imageUrl'] ?? '',
      videoUrl: json['videoUrl'] ?? '',
      courseId: json['courseId'],
      courseEventId: json['courseEventId'] ?? '',
      dateCreate: json['dateCreate'] ?? '',
      dateChange: json['dateChange'] ?? '',
      changedBy: json['changedBy'] ?? '',
      free: json['free'] ?? false,
      deleted: json['deleted'] ?? false,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'trainingPartName': trainingPartName,
      'startTime': startTime,
      'endTime': endTime,
      'description': description,
      'trainingPartType': trainingPartType,
      'imageUrl': imageUrl,
      'videoUrl': videoUrl,
      'courseId': courseId,
      'courseEventId': courseEventId,
      'dateCreate': dateCreate,
      'dateChange': dateChange,
      'changedBy': changedBy,
      'free': free,
      'deleted': deleted,
    };
  }
}
