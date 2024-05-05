//package card.cardapio.services;
//
//import card.cardapio.dto.comments.ComentarioRequestDto;
//import card.cardapio.entitie.Comments;
//import card.cardapio.entitie.Users;
//import card.cardapio.repositories.CommentsRepository;
//import card.cardapio.repositories.UserRepository;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//@Service
//public class CommentsService {
//
//    private final UserRepository userRepository;
//    private final CommentsRepository repository;
//
//    public CommentsService(CommentsRepository repository, UserRepository userRepository) {
//        this.repository = repository;
//        this.userRepository = userRepository;
//    }
//
//    public Comments saveComment(ComentarioRequestDto requestDto) {
//        Optional<Users> usersOptional = userRepository.findById(requestDto.getUsuarioId());
//        if (usersOptional.isPresent()) {
//           Users users = usersOptional.get();
//            Comments comment = new Comments();
//            comment.setUsers(users);
//            comment.setText(requestDto.getTexto());
//            return repository.save(comment);
//        }
//        else {
//            throw new IllegalArgumentException("User not found");
//        }
//    }
//}
